# Posts Service

This is a simple Spring Boot application that manages posts for a specific application. This service integrates user authentication from [Auth Service](https://github.com/MiguelCastilloSanchez/spring-boot-auth-service). It also contains the CI/CD pipeline for building and deploying the service to Dockerhub and the deployment repository at [Helm Chart App Services](https://github.com/MiguelCastilloSanchez/helm-chart-app-services).

## Endpoints

### Public Endpoints

#### Get Paginated Posts
**GET** `/posts/page/{page}`
- **Description**: Retrieves paginated posts.
- **Path Parameters**:
  - `page` (int): Page number.

#### Get Paginated Posts from a Specific User
**GET** `/posts/page/{page}/{userId}`
- **Description**: Retrieves paginated posts created by a specific user.
- **Path Parameters**:
  - `page` (int): Page number.
  - `userId` (String): ID of the user.

---

### USER Role Endpoints

#### Add Post
**POST** `/posts/add-post`
- **Description**: Creates and saves a new post.
- **Headers**:
  - `Authorization` (String): User's token.
- **Request Body** (JSON):
  ```json
  {
    "songName": "string",
    "songAuthor": "string",
    "songReview": "string"
  }
  ```

#### Like or Unlike Post
**POST** `/posts/{postId}/like`
- **Description**: Likes or unlikes a post.
- **Path Parameters**:
  - `postId` (String): ID of the post.
- **Headers**:
  - `Authorization` (String): User's token.

#### Delete Post
**DELETE** `/posts/{postId}/delete`
- **Description**: Deletes a post created by the authenticated user.
- **Path Parameters**:
  - `postId` (String): ID of the post.
- **Headers**:
  - `Authorization` (String): User's token.

---

### ADMIN Role Endpoints

#### Remove Any Post
**DELETE** `/posts/{postId}/remove`
- **Description**: Deletes any post regardless of ownership.
- **Path Parameters**:
  - `postId` (String): ID of the post.

---

## Notes
- **Authorization**: Some endpoints require an `Authorization` header with a valid JWT token.
- **Validation**: Inputs are validated, and appropriate error messages are returned for invalid requests.
- **Roles**: Specific endpoints are restricted to users with the `ADMIN` role.