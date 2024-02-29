const { createApp } = Vue;

createApp({
  data() {
    return {
      cardType: "",
      cardColor: "",
      messageError:""
    };
  },
  created() {},
  methods: {
    createCard() {
      axios
        .post(
          "/api/clients/current/cards",
          `type=${this.cardType}&color=${this.cardColor}`
        )
        .then((response) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Your card was created successfully",
            showConfirmButton: false,
            timer: 1500,
          }),
            setTimeout(() => {
              location.pathname = "/web/assets/pages/card.html";
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
  },
  
}).mount("#app");
