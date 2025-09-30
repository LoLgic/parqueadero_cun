const form = document.getElementById("registerForm");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const nuevoUsuario = {
    nombre: document.getElementById("nombre").value,
    apellido: document.getElementById("apellido").value,
    celular: document.getElementById("celular").value,
    email: document.getElementById("email").value,
    clave: document.getElementById("clave").value,
  };

  try {
    const response = await fetch("http://localhost:8081/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevoUsuario),
    });

    if (!response.ok) {
      let errorMessage = "No se pudo registrar el usuario";
      try {
        const errorData = await response.json();
        errorMessage = errorData.message || errorMessage;
      } catch {
        errorMessage = `Error ${response.status}: ${response.statusText}`;
      }
      document.getElementById("mensajeError").innerText = errorMessage;
      document.getElementById("mensajeExito").innerText = "";
      return;
    }

    const data = await response.json();

    // Si quieres redirigir automáticamente al login:
    setTimeout(() => {
      window.location.href = "login.html";
    }, 1000);

  } catch (err) {
    console.error("Error de red:", err);
    document.getElementById("mensajeError").innerText =
      "Error de conexión con el servidor";
    document.getElementById("mensajeExito").innerText = "";
  }
});
