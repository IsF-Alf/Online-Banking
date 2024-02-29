const { createApp } = Vue;

createApp({
  data() {
    return {
      loans: [],
      loanName: "",
      maxAmount: 0,
      payments: [],
      interestPercentage: 0,
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        client = response.data;
        console.log(client);
      })
      .catch((err) => console.log(err));
  },
  methods: {
    createNewLoan() {
      Swal.fire({
        title: "Are you sure?",
        text: "Are you sure you want to create this loan?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, create this loan!",
      }).then((result) => {
        if (result.isConfirmed) {
          const newLoan = `name=${this.loanName}&maxAmount=${this.maxAmount}&payments=${this.payments}&interestPercentage=${this.interestPercentage}`;
          axios
            .post("/api/admin/loans", newLoan)
            .then((result) => {
              Swal.fire({
                title: "Created!",
                text: "The loan has been created successfully",
                icon: "success",
              });
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
        }
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
  },
}).mount("#app");
