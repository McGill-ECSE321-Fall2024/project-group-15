<template>
  <div class="staff-review-page">
    <h1>All Reviews</h1>
    <div v-for="(reviews, gameTitle) in groupedReviews" :key="gameTitle">
      <h2>Reviews for {{ gameTitle }}</h2>
      <div v-for="review in reviews" :key="review.id" :class="{ response: review.isResponse }">
        <p><strong>{{ review.isResponse ? `Responding to ${review.responseTo}` : review.username }}:</strong></p>
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
        const response = await axiosClient.get("/reviews/grouped");
        this.groupedReviews = response.data;
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

<style>
.staff-review-page {
  padding: 20px;
}

.response {
  margin-left: 20px;
  background-color: #f5f5f5;
  border-left: 3px solid #007bff;
  padding-left: 10px;
}

.manager-response {
  margin-top: 10px;
}

textarea {
  width: 100%;
  padding: 8px;
  margin-top: 8px;
  resize: vertical;
}

button {
  margin-top: 8px;
  background-color: #007bff;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
