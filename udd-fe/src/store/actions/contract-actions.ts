import { createAsyncThunk } from "@reduxjs/toolkit";
import { Contract } from "../../types/document";

export const submitContract = createAsyncThunk<Contract, File>(
  "contarct/submitContract",
  async (file: File, { rejectWithValue }) => {
    try {
      const formData = new FormData();
      formData.append("file", file);
      const response = await fetch("http://localhost:8080/api/index/contract/save", {
        method: "POST",
        body: formData,
      });
      if (!response.ok) return rejectWithValue("Failed to submit contract");
      return await response.json();
    } catch (error) {
      return rejectWithValue("Failed to submit contract");
    }
  }
);
