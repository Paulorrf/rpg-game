import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";

interface DataProps {
  name: String;
  email: String;
}

const handleCreate: SubmitHandler<DataProps> = async (data) => {
  const { name, email } = data;

  const resp = await fetch("/api/users", {
    method: "POST",
    body: JSON.stringify({ name, email }),
  });

  if (!resp.ok) {
    throw new Error(resp.statusText);
  }

  return await resp.json();
};

const register = () => {
  const { register, handleSubmit } = useForm<DataProps>();

  console.log(register);

  return (
    <div>
      <form onSubmit={handleSubmit(handleCreate)}>
        <input type="text" placeholder="name" {...register("name")} />
        <input type="text" placeholder="email" {...register("email")} />
        <button type="submit">criar usuario</button>
      </form>
    </div>
  );
};

export default register;
