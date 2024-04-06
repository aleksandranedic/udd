import { createAsyncThunk } from "@reduxjs/toolkit";
import { Contract } from "../../types/document";

export const submitContract = createAsyncThunk<Contract, File>(
  "contarct/submitContract",
  async (file: File, { rejectWithValue }) => {
    const formData = new FormData();
    formData.append("file", file);
    const response = await fetch("http://localhost:3000/api/index/contract", {
      method: "POST",
      body: formData,
    });
    if (!response) return rejectWithValue("Failed to submit contract");
    return response.json() as Promise<Contract>;
  }
);
