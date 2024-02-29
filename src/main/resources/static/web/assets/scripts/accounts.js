const { createApp } = Vue;

createApp({
  data() {
    return {
      clientsInformation: {},
      accounts: [],
      loans: [],
      accountType: null,
      userEmail: "",
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        console.log(response);
        client = response.data;
        this.clientsInformation = client;
        console.log(client);
        this.accounts = client.accounts;
        this.accounts.sort((a, b) => a.id - b.id);
        console.log(this.accounts);
        this.loans = client.loans;
        console.log(this.loans);
        this.userEmail = client.email;
      })
      .catch((error) => console.log(error));
  },
  methods: {
    logoutClient() {
      axios
        .post("/api/logout")
        .then((response) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Logged out successfully",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = `/index.html`;
            }, 1600);
          console.log("signed out!!!");
        })
        .catch((error) => console.log(error));
    },
    createAccount() {
      axios
        .post(
          `/api/clients/current/accounts`,
          `accountType=${this.accountType}`
        )
        .then((response) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your new account was created successfully",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.reload();
            }, 1600);
        })
        .catch((error) => {
          Swal.fire({
            icon: "error",
            text: error.response.data,
            confirmButtonColor: "#ff0000",
          });
        });
    },
    deleteAccount(id) {
      axios
        .patch(`/api/clients/current/accounts`, `id=${id}`)
        .then(() => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your account was deleted successfully",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = `/web/accounts.html`;
            }, 1600);
        })
        .catch((error) => {
          Swal.fire({
            icon: "error",
            text: error.response.data,
            confirmButtonColor: "#ff0000",
          });
        });
    },
  },
}).mount("#app");
