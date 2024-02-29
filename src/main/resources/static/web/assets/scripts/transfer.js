const app = Vue.createApp({
  data() {
    return {
      accounts: {},
      amount: null,
      description: "",
      originNumber: "",
      destinationNumber: "",
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.accounts = response.data.accounts;
        console.log(this.accounts);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    generateTransfer() {
      axios
        .post(
          `/api/clients/current/transfers`,
          `amount=${this.amount}&description=${this.description}&originNumber=${this.originNumber}&destinationNumber=${this.destinationNumber}`
        )
        .then(() => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your transaction was successful",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = `/web/assets/pages/transfers.html`;
            }, 1600);
        })
        .catch((error) => console.log(error));
    },
  },
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
});
app.mount("#app");
