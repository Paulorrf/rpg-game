import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

interface DataProps {
  name: String;
  email: String;
  password: String;
}

const handleCreate: SubmitHandler<DataProps> = async (data) => {
  const { name, email, password } = data;

  const resp = await fetch("/api/users", {
    method: "POST",
    body: JSON.stringify({ name, email, password }),
  });

  if (!resp.ok) {
    throw new Error(resp.statusText);
  }

  return await resp.json();
};

const schema = yup
  .object({
    name: yup.string().matches(/^[a-zA-Z]+$/),
    email: yup.string().email(),
    password: yup.string(),
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

  const [handleCheckbox, setHandleCheckbox] = useState(false);

  console.log(errors);

  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center">
      <h2 className="mb-4">Register a new account!</h2>
      <form
        className="flex  flex-col items-center justify-center rounded border p-4 text-center shadow-md shadow-slate-700"
        onSubmit={handleSubmit(handleCreate)}
      >
        <div className="mb-12">
          <input
            className=" border p-2 text-center"
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
            className=" border p-2 text-center"
            type="text"
            placeholder="email"
            {...register("email")}
          />
          <p className="text-red-600">
            {errors.email && "Please add a valid email!"}
          </p>
        </div>
        <div className="mb-12 inline-block">
          <input
            className=" border p-2 text-center"
            type={handleCheckbox ? "text" : "password"}
            placeholder="password"
            {...register("password")}
          />
          <div className="mt-2 flex items-center justify-center">
            <input
              type="checkbox"
              id="check"
              onClick={() => setHandleCheckbox((prev) => !prev)}
            />
            <label className="ml-2" htmlFor="check">
              Show Password
            </label>
          </div>
          <p className="text-red-600">
            {errors.email && "Please add a valid password!"}
          </p>
        </div>
        <button
          className="w-full rounded border bg-cyan-600 py-2 text-white"
          type="submit"
        >
          criar usuario
        </button>
      </form>
    </div>
  );
};

export default register;
