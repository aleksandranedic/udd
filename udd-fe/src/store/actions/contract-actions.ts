import { createAsyncThunk } from "@reduxjs/toolkit";
import { Contract } from "../../types/document";

export const submitContract = createAsyncThunk<Contract, string>(
  "contarct/submitContract",
  async (file: string, { rejectWithValue }) => {
    await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulating async operation
    console.log(file);
    const result = {
      governmentName: "",
      governmentAddress: "",
      governmentAdministrativeLevel: "",
      governmentEmail: "",
      governmentPhone: "",
      content: "",
      agencyEmployeeName: "",
      agencyAddress: "",
      agencyEmail: "",
      agencyPhone: "",
      title: "",
      governmentEmployeeName: "",
      loading: false,
    };
    if (!result) return rejectWithValue("Failed to submit contract");
    return result;
  }
);
