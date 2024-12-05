<template>
  <div class="staff-review-page">
    <h1>All Reviews</h1>
    <div v-for="(reviews, gameTitle) in groupedReviews" :key="gameTitle">
      <h2>Reviews for {{ gameTitle }}</h2>
      <div v-for="review in reviews" :key="review.id" :class="{ response: review.isResponse }">
        <p>
          <strong>
            {{ review.isResponse ? `Responding to ${review.responseTo}` : review.username }}:
          </strong>
        </p>
        <p>{{ review.description }}</p>
        <p><strong>Rating:</strong> {{ review.rating }}</p>
        <div v-if="!review.isResponse && isManager" class="manager-response">
          <textarea v-model="responses[review.id]" placeholder="Write a response..."></textarea>
          <button @click="submitResponse(review.id, review.username)">Respond</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosClient from "./axios";

export default {
  data() {
    return {
      groupedReviews: {}, // Reviews grouped by game title
      responses: {}, // Temporary storage for manager responses
      isManager: false, // Assume this is determined by user role
    };
  },
  created() {
    this.fetchGroupedReviews();
    this.checkManagerStatus();
  },
  methods: {
    async fetchGroupedReviews() {
      try {
        const response = await axiosClient.get("/reviews/");
        this.groupedReviews = response.data || [];
      } catch (error) {
        console.error("Error fetching reviews:", error);
      }
    },
    checkManagerStatus() {
      // Determine if the user is a manager
      const userRole = localStorage.getItem("userRole");
      this.isManager = userRole === "manager";
    },
    async submitResponse(reviewId, username) {
      const gameId = this.findGameIdByReviewId(reviewId);
      const responseDescription = `Responding to ${username}'s review: ${this.responses[reviewId]}`;
      
      if (!gameId || !this.responses[reviewId]?.trim()) {
        alert("Please write a response before submitting.");
        return;
      }

      try {
        const reviewData = {
          rating: "N/A", // Optional, or set a default value
          description: responseDescription,
          game: { gameID: gameId },
        };
        await axiosClient.post("/review", reviewData);
        alert("Response added successfully!");
        this.fetchGroupedReviews(); // Refresh reviews
      } catch (error) {
        console.error("Error submitting response:", error);
        alert("Failed to submit response.");
      }
    },
    findGameIdByReviewId(reviewId) {
      // Helper function to find the game ID for a given review
      for (const [gameTitle, reviews] of Object.entries(this.groupedReviews)) {
        const review = reviews.find((r) => r.id === reviewId);
        if (review) return review.gameId; // Assuming each review object has a `gameId`
      }
      return null;
    },
  },
};
</script>

<style scoped>
.staff-review-page {
  padding-top: 70px; /* Adjust to match the height of the NavBar */
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh; /* Ensure full page height */
  box-sizing: border-box; /* Include padding in the height */
}

h1 {
  padding-top: 70px; 
  text-align: center;
  color: #333;
}

h2 {
  margin-top: 20px;
  color: #555;
}

.response {
  margin-left: 20px;
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.manager-response {
  margin-top: 10px;
}

textarea {
  width: 100%;
  min-height: 50px;
  margin-top: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

button {
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
