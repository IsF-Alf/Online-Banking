const { createApp } = Vue;

createApp({
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      emailRegister: "",
      passwordRegister: "",
      msjError: "",
      showRegisterForm: false,
    };
  },
  created() {},
  methods: {
    loginClient() {
      const clientLogin = `email=${this.email}&password=${this.password}`;
      axios
        .post("/api/login", clientLogin)
        .then((response) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Logged in successfully",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = `/web/accounts.html`;
            }, 1600);
          console.log("signed in!!!");
        })
        .catch((error) => {
          Swal.fire({
            icon: "error",
            text: error.response.data,
            confirmButtonColor: "#ff0000",
          });
          console.log(error.response.data);
        });
    },
    registerClient() {
      const clientsData = `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailRegister}&password=${this.passwordRegister}`;
      axios
        .post("/api/clients", clientsData)
        .then((response) => {
          const clientLogin = `email=${this.emailRegister}&password=${this.passwordRegister}`;
          axios.post("/api/login", clientLogin).then((response) => {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Logged in successfully",
              showConfirmButton: false,
              timer: 1500,
            }),
              setTimeout(() => {
                location.pathname = `/web/accounts.html`;
              }, 1600);
          });
        })
        .catch((error) => {
          Swal.fire({
            icon: "error",
            text: error.response.data,
            confirmButtonColor: "#ff0000",
          });
          console.log(error.response.data);
        });
    },
    toggleRegisterForm() {
      this.showRegisterForm = !this.showRegisterForm;
    },
  },
}).mount("#app");
