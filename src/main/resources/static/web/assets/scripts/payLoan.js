const { createApp } = Vue;

createApp({
  data() {
    return {
      client: {},
      accounts: [],
      loans: [],
      amount: null,
      idLoan: 0,
      idAccount: 0,
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        client = response.data;
        console.log(client);
        this.loans = client.loans;
        console.log(this.loans);
        this.accounts = client.accounts;
        console.log(this.accounts);
      })
      .catch((err) => console.log(err));
  },
  methods: {
    payLoan() {
      axios
        .post(
          `/api/loans/payments`,
          `idLoan=${this.idLoan}&idAccount=${this.idAccount}&amount=${this.amount}`
        )
        .then(() => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your payment was successful",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = "/web/assets/pages/payLoan.html";
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
          console.log("created");
          location.reload();
        })
        .catch((error) => console.log("error"));
    },
    deleteAccount(id) {
      axios
        .patch(`/api/clients/current/accounts`, `id=${id}`)
        .then(() => {
          location.pathname = `/web/accounts.html`;
        })
        .catch((error) => {
          alert(error.response.data);
        });
    },
  },
}).mount("#app");
