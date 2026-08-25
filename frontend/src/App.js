import React, { useEffect, useState } from "react";
import { API_BASE_URL, APP_NAME } from "./config";

function App() {
  const [greeting, setGreeting] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`${API_BASE_URL}/api/greeting`)
      .then((res) => res.json())
      .then(setGreeting)
      .catch((err) => setError(err.message));
  }, []);

  return (
    <div style={{ fontFamily: "sans-serif", padding: "2rem" }}>
      <h1>{APP_NAME}</h1>
      {error && <p style={{ color: "red" }}>Error reaching backend: {error}</p>}
      {greeting ? (
        <div>
          <p>Message: {greeting.message}</p>
          <p>Backend version: {greeting.version}</p>
        </div>
      ) : (
        !error && <p>Loading...</p>
      )}
    </div>
  );
}

export default App;
