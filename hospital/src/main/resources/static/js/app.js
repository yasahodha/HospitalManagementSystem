(function () {
  "use strict";

  const API = {
    doctors: "/doctor",
    patients: "/patient",
    employees: "/employee",
    appointments: "/appointment"
  };

  function $(id) {
    return document.getElementById(id);
  }

  function setNotice(message) {
    const notice = $("pageNotice");
    if (!notice) {
      return;
    }
    if (!message) {
      notice.hidden = true;
      notice.textContent = "";
      return;
    }
    notice.textContent = message;
    notice.hidden = false;
  }

  async function fetchJson(url, options) {
    const response = await fetch(url, options);
    if (!response.ok) {
      const text = await response.text();
      throw new Error(text || ("Request failed: " + response.status));
    }
    if (response.status === 204) {
      return null;
    }
    return response.json();
  }

  async function postJson(url, data) {
    return fetchJson(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });
  }

  async function deleteRequest(url) {
    const response = await fetch(url, { method: "DELETE" });
    if (!response.ok) {
      const text = await response.text();
      throw new Error(text || ("Delete failed: " + response.status));
    }
  }

  async function postRequest(url) {
    const response = await fetch(url, { method: "POST" });
    if (!response.ok) {
      const text = await response.text();
      throw new Error(text || ("Request failed: " + response.status));
    }
  }

  function formatText(value) {
    if (value === null || value === undefined || value === "") {
      return "N/A";
    }
    return String(value);
  }

  function setActiveNav(page) {
    const links = document.querySelectorAll("nav a");
    links.forEach((link) => {
      if (link.dataset.page === page) {
        link.classList.add("active");
      } else {
        link.classList.remove("active");
      }
    });
  }

  async function initDashboard() {
    setActiveNav("home");
    setNotice("");
    try {
      const [appointments, patients, doctors, employees] = await Promise.all([
        fetchJson(API.appointments),
        fetchJson(API.patients),
        fetchJson(API.doctors),
        fetchJson(API.employees)
      ]);

      $("appointmentCount").textContent = appointments.length;
      $("patientCount").textContent = patients.length;
      $("doctorCount").textContent = doctors.length;
      $("employeeCount").textContent = employees.length;

      const tbody = $("recentAppointmentsBody");
      if (!tbody) {
        return;
      }

      const recent = appointments.slice().sort((a, b) => (b.id || 0) - (a.id || 0)).slice(0, 5);
      if (recent.length === 0) {
        tbody.innerHTML = "<tr><td colspan=\"5\">No appointments yet.</td></tr>";
        return;
      }

      tbody.innerHTML = recent.map((a) => {
        const patientName = a.patient ? a.patient.name : (a.patientName || "N/A");
        const doctorName = a.doctor ? a.doctor.name : (a.doctorName || "N/A");
        return "<tr>" +
          "<td>" + formatText(a.id) + "</td>" +
          "<td>" + formatText(patientName) + "</td>" +
          "<td>" + formatText(doctorName) + "</td>" +
          "<td>" + formatText(a.appointmentDate) + "</td>" +
          "<td>" + formatText(a.appointmentTime) + "</td>" +
        "</tr>";
      }).join("");
    } catch (error) {
      setNotice(error.message);
    }
  }

  async function initDoctors() {
    setActiveNav("doctors");
    const tbody = $("doctorsTableBody");
    const form = $("doctorForm");
    const nameInput = $("doctorName");
    const specialityInput = $("doctorSpeciality");

    async function loadDoctors() {
      setNotice("");
      try {
        const doctors = await fetchJson(API.doctors);
        if (!tbody) {
          return;
        }
        if (doctors.length === 0) {
          tbody.innerHTML = "<tr><td colspan=\"4\">No doctors added yet.</td></tr>";
          return;
        }
        tbody.innerHTML = doctors.map((d) => {
          return "<tr>" +
            "<td>" + formatText(d.id) + "</td>" +
            "<td>" + formatText(d.name) + "</td>" +
            "<td>" + formatText(d.speciality) + "</td>" +
            "<td><button class=\"btn btn-danger\" data-delete-id=\"" + d.id + "\">Remove</button></td>" +
          "</tr>";
        }).join("");
      } catch (error) {
        setNotice(error.message);
      }
    }

    if (form) {
      form.addEventListener("submit", async (event) => {
        event.preventDefault();
        setNotice("");
        try {
          await postJson("/doctor/add", {
            name: nameInput.value.trim(),
            speciality: specialityInput.value.trim()
          });
          form.reset();
          loadDoctors();
        } catch (error) {
          setNotice(error.message);
        }
      });
    }

    if (tbody) {
      tbody.addEventListener("click", async (event) => {
        const target = event.target;
        if (target && target.dataset.deleteId) {
          const id = target.dataset.deleteId;
          if (!confirm("Remove this doctor?")) {
            return;
          }
          setNotice("");
          try {
            await postRequest("/doctor/delete/" + id);
            loadDoctors();
          } catch (error) {
            setNotice(error.message);
          }
        }
      });
    }

    loadDoctors();
  }

  async function initPatients() {
    setActiveNav("patients");
    const tbody = $("patientsTableBody");
    const form = $("patientForm");
    const nameInput = $("patientName");
    const genderInput = $("patientGender");
    const dobInput = $("patientDob");

    async function loadPatients() {
      setNotice("");
      try {
        const patients = await fetchJson(API.patients);
        if (!tbody) {
          return;
        }
        if (patients.length === 0) {
          tbody.innerHTML = "<tr><td colspan=\"5\">No patients added yet.</td></tr>";
          return;
        }
        tbody.innerHTML = patients.map((p) => {
          return "<tr>" +
            "<td>" + formatText(p.id) + "</td>" +
            "<td>" + formatText(p.name) + "</td>" +
            "<td>" + formatText(p.gender) + "</td>" +
            "<td>" + formatText(p.dob) + "</td>" +
            "<td><button class=\"btn btn-danger\" data-delete-id=\"" + p.id + "\">Remove</button></td>" +
          "</tr>";
        }).join("");
      } catch (error) {
        setNotice(error.message);
      }
    }

    if (form) {
      form.addEventListener("submit", async (event) => {
        event.preventDefault();
        setNotice("");
        try {
          await postJson("/patient/add", {
            name: nameInput.value.trim(),
            gender: genderInput.value,
            dob: dobInput.value
          });
          form.reset();
          loadPatients();
        } catch (error) {
          setNotice(error.message);
        }
      });
    }

    if (tbody) {
      tbody.addEventListener("click", async (event) => {
        const target = event.target;
        if (target && target.dataset.deleteId) {
          const id = target.dataset.deleteId;
          if (!confirm("Remove this patient?")) {
            return;
          }
          setNotice("");
          try {
            await deleteRequest("/patient/delete/" + id);
            loadPatients();
          } catch (error) {
            setNotice(error.message);
          }
        }
      });
    }

    loadPatients();
  }

  async function initEmployees() {
    setActiveNav("employees");
    const tbody = $("employeesTableBody");
    const form = $("employeeForm");
    const nameInput = $("employeeName");
    const positionInput = $("employeePosition");
    const hireDateInput = $("employeeHireDate");

    async function loadEmployees() {
      setNotice("");
      try {
        const employees = await fetchJson(API.employees);
        if (!tbody) {
          return;
        }
        if (employees.length === 0) {
          tbody.innerHTML = "<tr><td colspan=\"5\">No employees added yet.</td></tr>";
          return;
        }
        tbody.innerHTML = employees.map((e) => {
          return "<tr>" +
            "<td>" + formatText(e.id) + "</td>" +
            "<td>" + formatText(e.name) + "</td>" +
            "<td>" + formatText(e.position) + "</td>" +
            "<td>" + formatText(e.hireDate) + "</td>" +
            "<td><button class=\"btn btn-danger\" data-delete-id=\"" + e.id + "\">Remove</button></td>" +
          "</tr>";
        }).join("");
      } catch (error) {
        setNotice(error.message);
      }
    }

    if (form) {
      form.addEventListener("submit", async (event) => {
        event.preventDefault();
        setNotice("");
        try {
          await postJson("/employee/add", {
            name: nameInput.value.trim(),
            position: positionInput.value.trim(),
            hireDate: hireDateInput.value
          });
          form.reset();
          loadEmployees();
        } catch (error) {
          setNotice(error.message);
        }
      });
    }

    if (tbody) {
      tbody.addEventListener("click", async (event) => {
        const target = event.target;
        if (target && target.dataset.deleteId) {
          const id = target.dataset.deleteId;
          if (!confirm("Remove this employee?")) {
            return;
          }
          setNotice("");
          try {
            await deleteRequest("/employee/delete/" + id);
            loadEmployees();
          } catch (error) {
            setNotice(error.message);
          }
        }
      });
    }

    loadEmployees();
  }

  async function initAppointments() {
    setActiveNav("appointments");
    const tbody = $("appointmentsTableBody");
    const form = $("appointmentForm");
    const doctorSelect = $("appointmentDoctorId");

    const patientNameInput = $("appointmentPatientName");
    const genderInput = $("appointmentGender");
    const dobInput = $("appointmentDob");
    const dateInput = $("appointmentDate");
    const timeInput = $("appointmentTime");
    const detailsInput = $("appointmentDetails");

    async function loadDoctors() {
      setNotice("");
      try {
        const doctors = await fetchJson(API.doctors);
        if (!doctorSelect) {
          return;
        }
        if (doctors.length === 0) {
          doctorSelect.innerHTML = "<option value=\"\" disabled selected>No doctors available</option>";
          setNotice("Add at least one doctor before booking an appointment.");
          return;
        }
        doctorSelect.innerHTML = "<option value=\"\" disabled selected>Select doctor</option>" +
          doctors.map((d) => {
            return "<option value=\"" + d.id + "\">" + d.name + " - " + d.speciality + "</option>";
          }).join("");
      } catch (error) {
        setNotice(error.message);
      }
    }

    async function loadAppointments() {
      setNotice("");
      try {
        const appointments = await fetchJson(API.appointments);
        if (!tbody) {
          return;
        }
        if (appointments.length === 0) {
          tbody.innerHTML = "<tr><td colspan=\"6\">No appointments yet.</td></tr>";
          return;
        }
        tbody.innerHTML = appointments.map((a) => {
          const patientName = a.patient ? a.patient.name : (a.patientName || "N/A");
          const doctorName = a.doctor ? a.doctor.name : (a.doctorName || "N/A");
          return "<tr>" +
            "<td>" + formatText(a.id) + "</td>" +
            "<td>" + formatText(patientName) + "</td>" +
            "<td>" + formatText(doctorName) + "</td>" +
            "<td>" + formatText(a.appointmentDate) + "</td>" +
            "<td>" + formatText(a.appointmentTime) + "</td>" +
            "<td><button class=\"btn btn-danger\" data-delete-id=\"" + a.id + "\">Remove</button></td>" +
          "</tr>";
        }).join("");
      } catch (error) {
        setNotice(error.message);
      }
    }

    if (form) {
      form.addEventListener("submit", async (event) => {
        event.preventDefault();
        setNotice("");
        const doctorId = doctorSelect.value;
        if (!doctorId) {
          setNotice("Select a doctor before submitting.");
          return;
        }
        try {
          await postJson("/appointment/add", {
            patientName: patientNameInput.value.trim(),
            gender: genderInput.value,
            dob: dobInput.value,
            doctorId: Number(doctorId),
            appointmentDate: dateInput.value,
            appointmentTime: timeInput.value,
            details: detailsInput.value.trim()
          });
          form.reset();
          loadAppointments();
          loadDoctors();
        } catch (error) {
          setNotice(error.message);
        }
      });
    }

    if (tbody) {
      tbody.addEventListener("click", async (event) => {
        const target = event.target;
        if (target && target.dataset.deleteId) {
          const id = target.dataset.deleteId;
          if (!confirm("Remove this appointment?")) {
            return;
          }
          setNotice("");
          try {
            await deleteRequest("/appointment/delete/" + id);
            loadAppointments();
          } catch (error) {
            setNotice(error.message);
          }
        }
      });
    }

    loadDoctors();
    loadAppointments();
  }

  document.addEventListener("DOMContentLoaded", () => {
    const page = document.body.dataset.page;
    if (page === "home") {
      initDashboard();
    } else if (page === "appointments") {
      initAppointments();
    } else if (page === "patients") {
      initPatients();
    } else if (page === "doctors") {
      initDoctors();
    } else if (page === "employees") {
      initEmployees();
    }
  });
})();
