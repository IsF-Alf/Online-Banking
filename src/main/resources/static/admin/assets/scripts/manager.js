const { createApp } = Vue;

createApp({
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      clientsInformation: [],
      clientsData: [],
      respuestaJSON: "",
    };
  },
  created() {
    axios
      .get("/api/clients")
      .then((response) => {
        apiResp = response.data;
        this.clientsInformation = apiResp;
        this.respuestaJSON = apiResp;
        console.log(apiResp);
      })
      .catch((error) => console.log(error));
  },
  methods: {
    addUser() {
      const clientsData = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
      };
      axios
        .post("/rest/clients", clientsData)
        .then((response) => {
          alert("Usuario creado");
          console.log(response);
        })
        .catch((error) => console.log(error));
    },
  },
}).mount("#app");