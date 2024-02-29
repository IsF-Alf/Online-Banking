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
    };
  },
  created() {},
  methods: {
    loginClient() {
      const clientLogin = `email=${this.email}&password=${this.password}`;
      axios
        .post("/api/login", clientLogin)
        .then((response) => {
          console.log("signed in!!!");
          location.pathname = "/web/accounts.html";
        })
        .catch((error) => console.log(error));
    },
    logoutClient() {
      axios.post("/api/logout").then((response) => {
        console.log("signed out!!!");
        location.pathname = `/index.html`;
      });
    },
    registerClient() {
      const clientsData = `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailRegister}&password=${this.passwordRegister}`;
      axios
        .post("/api/clients", clientsData)
        .then((response) => {
          const clientLogin = `email=${this.emailRegister}&password=${this.passwordRegister}`;
          axios.post("/api/login", clientLogin).then((response) => {
            location.pathname = "/web/accounts.html";
          });
          console.log(response);
          location.pathname = "/web/accounts.html";
        })
        .catch((error) => console.log(error));
    },
  },
}).mount("#app");
