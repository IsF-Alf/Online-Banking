const { createApp } = Vue;

createApp({
  data() {
    return {
      accounts: [],
      transactions: [],
      account: ``,
    };
  },
  created() {
    let param = location.search;
    let params = new URLSearchParams(param);
    let idClient = params.get("id");    
    axios
      .get(`/api/accounts/${idClient}`)
      .then((accountsAll) => {
        this.accounts = accountsAll.data;
        this.transactions = this.accounts.transactions;
        this.transactions.sort((a, b) => b.id - a.id);
        this.account = this.accounts.number;
        console.log(this.accounts);
        console.log(this.accounts.transactions);
      })
      .catch((err) => console.log(err));
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
  },
}).mount("#app");
