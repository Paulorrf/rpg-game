import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { string } from "yup/lib/locale";

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

const schema = yup
  .object({
    name: yup.string().matches(/^[a-zA-Z]$/),
    email: yup.string().email(),
  })
  .required();

const register = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<DataProps>({
    resolver: yupResolver(schema),
  });

  return (
    <div className="flex flex-col justify-center items-center w-screen h-screen">
      <h2 className="mb-4">Register a new account!</h2>
      <form
        className="flex  shadow-md p-4 border rounded shadow-slate-700 flex-col justify-center text-center items-center"
        onSubmit={handleSubmit(handleCreate)}
      >
        <div className="mb-12">
          <input
            className=" text-center border p-2"
            type="text"
            placeholder="name"
            {...register("name")}
          />
          <p className="text-red-600">
            {errors.name && "Please add a valid name!"}
          </p>
        </div>

        <div className="mb-12">
          <input
            className=" text-center border p-2"
            type="text"
            placeholder="email"
            {...register("email")}
          />
          <p className="text-red-600">
            {errors.email && "Please add a valid email!"}
          </p>
        </div>
        <button
          className="border bg-cyan-600 text-white rounded w-full py-2"
          type="submit"
        >
          criar usuario
        </button>
      </form>
    </div>
  );
};

export default register;
