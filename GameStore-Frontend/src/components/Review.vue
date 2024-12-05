<template>
  <div class="review-page">
    <h1>Add a Review</h1>
    <form @submit.prevent="submitReview">
      <!-- Game Title -->
      <div class="form-group">
        <label for="game-title">Game Title</label>
        <input
          type="text"
          id="game-title"
          v-model="gameTitle"
          placeholder="Enter the game title"
          required
        />
      </div>

      <!-- Customer ID -->
      <div class="form-group">
        <label for="customer-id">Customer ID</label>
        <input
          type="text"
          id="customer-id"
          v-model="customerID"
          placeholder="Enter your customer ID"
          required
        />
      </div>

      <!-- Rating -->
      <div class="form-group">
        <label for="rating">Rating (1-5)</label>
        <select id="rating" v-model="rating" required>
          <option value="" disabled>Select a rating</option>
          <option v-for="star in 5" :key="star" :value="star">{{ star }}</option>
        </select>
      </div>

      <!-- Review Text -->
      <div class="form-group">
        <label for="review-text">Review</label>
        <textarea
          id="review-text"
          v-model="reviewText"
          placeholder="Write your review here"
          rows="5"
          required
        ></textarea>
      </div>

      <!-- Submit Button -->
      <button type="submit" class="submit-button">Add Review</button>
    </form>

    <!-- Error Message -->
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from "./axios";

export default {
  name: "Review",
  data() {
    return {
      gameTitle: "", // Game title input by the user
      customerID: "", // Customer ID input
      rating: "", // Rating input (1-5)
      reviewText: "", // Review text input
      errorMessage: "", // Error message for displaying any issues
    };
  },
  methods: {
    async submitReview() {
      try {
        // Fetch the gameID using the game title
        const gameResponse = await axios.get(`/games?title=${encodeURIComponent(this.gameTitle)}`);
        if (!gameResponse.data || gameResponse.data.length === 0) {
          this.errorMessage = "Game not found. Please check the game title.";
          return;
        }

        const gameID = gameResponse.data[0].gameID;

        // Prepare the review payload
        const reviewPayload = {
          gameID,
          customerID: this.customerID,
          rating: this.rating,
          review: this.reviewText,
        };

        // Submit the review to the backend
        await axios.post("/reviews", reviewPayload);

        // Clear form and show success message
        alert("Review added successfully!");
        this.gameTitle = "";
        this.customerID = "";
        this.rating = "";
        this.reviewText = "";
        this.errorMessage = "";
      } catch (error) {
        console.error("Error submitting review:", error);
        this.errorMessage = "An error occurred while submitting your review. Please try again.";
      }
    },
  },
};
</script>

<style scoped>
.review-page {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

input,
select,
textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

textarea {
  resize: none;
}

.submit-button {
  display: block;
  width: 100%;
  padding: 10px;
  font-size: 16px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #218838;
}

.error-message {
  color: red;
  margin-top: 10px;
  text-align: center;
}
</style>
