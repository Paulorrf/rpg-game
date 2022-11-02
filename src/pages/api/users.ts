import type { NextApiRequest, NextApiResponse } from "next";
import prisma from "../../../lib/prisma";

export default async (req: NextApiRequest, res: NextApiResponse) => {
  const userData = JSON.parse(req.body);

  const savedUser = await prisma.user.create({
    data: userData,
  });

  res.json(savedUser);
};
