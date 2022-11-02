import React, { useState } from "react";

const handleCreate = async (name: String, email: String) => {
  const user = { name, email };
  const resp = await fetch("/api/users", {
    method: "POST",
    body: JSON.stringify(user),
  });

  if (!resp.ok) {
    throw new Error(resp.statusText);
  }

  return await resp.json();
};

const register = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  return (
    <div>
      <form onSubmit={() => handleCreate(name, email)}>
        <input
          type="text"
          placeholder="name"
          onChange={(e: any) => setName(e.target.value)}
        />
        <input
          type="text"
          placeholder="email"
          onChange={(e: any) => setEmail(e.target.value)}
        />
        <button type="submit">criar usuario</button>
      </form>
    </div>
  );
};

export default register;
