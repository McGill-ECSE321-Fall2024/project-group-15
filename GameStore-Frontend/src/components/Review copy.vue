<template>
  <div class="review-page">
    <h1> All Reviews </h1>
    <div v-if="reviews.length > 0" class="reviews-list">
      <div v-for="review in reviews" :key="review.id" class="review-item">
        <p><strong>Rating:</strong> {{ review.rating }}</p>
        <p><strong>Description:</strong> {{ review.description }}</p>
      </div>
    </div>
    <div v-else>
      <p>No reviews available.</p>
    </div>
    <button @click="toggleAddReviewForm" class="add-review-button">Add Review</button>
    
    <div v-if="showAddReviewForm" class="add-review-form">
      <label for="rating">Rating:</label>
      <select v-model="newReview.rating">
        <option v-for="rating in ratings" :key="rating" :value="rating">
          {{ rating }}
        </option>
      </select>
      <textarea
        v-model="newReview.description"
        placeholder="Write your review..."
      ></textarea>
      <button @click="submitReview" class="submit-review-button">Submit</button>
    </div>

  <div v-for="review in reviews" :key="review.id" :class="{ response: review.isResponse }">
    <p><strong>{{ review.isResponse ? `Responding to ${review.responseTo}` : review.username }}:</strong></p>
    <p>{{ review.description }}</p>
    <p><strong>Rating:</strong> {{ review.rating }}</p>
  </div>

  </div>
</template>

<script>
import axiosClient from "./axios";

export default {
  data() {
    return {
      gameTitle: "",
      reviews: [],
      showAddReviewForm: false,
      newReview: {
        rating: "",
        description: "",
      },
      ratings: ["ONE_STAR", "TWO_STAR", "THREE_STAR", "FOUR_STAR", "FIVE_STAR"],
    };
  },
  created() {
    this.fetchReviews();
    this.fetchGameDetails();
  },
  methods: {
    async fetchReviews() {
      try {
        const response = await axiosClient.get(`/review/game/${this.$route.params.gameId}`);
        this.reviews = response.data;
      } catch (error) {
        console.error("Error fetching reviews:", error);
      }
    },
    async fetchGameDetails() {
      try {
        const response = await axiosClient.get(`/game/${this.$route.params.gameId}`);
        this.gameTitle = response.data.title;
      } catch (error) {
        console.error("Error fetching game details:", error);
      }
    },
    toggleAddReviewForm() {
      this.showAddReviewForm = !this.showAddReviewForm;
    },
    async submitReview() {
      try {
        const reviewData = {
          rating: this.newReview.rating,
          description: this.newReview.description,
          game: { gameID: this.$route.params.gameId },
        };
        await axiosClient.post("/review", reviewData);
        alert("Review added successfully!");
        this.newReview = { rating: "", description: "" };
        this.showAddReviewForm = false;
        this.fetchReviews(); // Refresh reviews list
      } catch (error) {
        console.error("Error adding review:", error);
        alert("Failed to add review.");
      }
    },
  },
};
</script>

<style>
.review-page {
  padding: 20px;
  background-color: #f9f9f9;
}

.reviews-list {
  margin-top: 20px;
}

.review-item {
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

h1{
  margin-top: 40px;
  color: #000000
  
}

p{
  color: #000000
}

label{
  color: #000000
}

.add-review-button {
  margin-top: 20px;
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-review-button:hover {
  background-color: #218838;
}

.add-review-form {
  margin-top: 20px;
}

.submit-review-button {
  margin-top: 10px;
  background-color: #0040ff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
}
</style>
