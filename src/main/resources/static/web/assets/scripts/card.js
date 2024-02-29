const { createApp } = Vue;

createApp({
  data() {
    return {
      clientsInformation: {},
      cards: [],
      creditCard: [],
      debitCard: [],
      currentDate:new Date(),
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        client = response.data;
        this.clientsInformation = client;
        console.log(client);
        this.cards = client.cards;
        console.log(this.cards);
        this.filterCredit();
        console.log(this.creditCard);
        this.filterDebit();
        console.log(this.debitCard);
      })
      .catch((error) => console.log(error));
  },
  methods: {
    deleteCard(id) {
      axios
        .patch(`/api/clients/current/cards`, `id=${id}`)
        .then(() => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your card was deleted successfully",
            showConfirmButton: false,
            timer: 1500
          }),
          setTimeout(() => {
            location.pathname = `/web/assets/pages/card.html`;
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
    filterCredit() {
      this.creditCard = this.cards.filter((card) => card.type === "CREDIT");
    },
    filterDebit() {
      this.debitCard = this.cards.filter((card) => card.type === "DEBIT");
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
