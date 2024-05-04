import { createAsyncThunk } from "@reduxjs/toolkit";
import { Contract, SearchResults } from "../../types/document";
import { ParameterEqualityOperand, ParameterObject } from "../../types/pages";

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

export const indexContract = createAsyncThunk<string, Contract>(
  "contarct/indexContract",
  async (contract: Contract, { rejectWithValue }) => {
    try {
      const response = await fetch("http://localhost:8080/api/index/contract", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(contract),
      });

      if (!response.ok) return rejectWithValue("Failed to submit contract");
      return await response.json();
    } catch (error) {
      return rejectWithValue("Failed to submit contract");
    }
  }
);

export const submitLaw = createAsyncThunk<Contract, File>(
  "contarct/submitLaw",
  async (file: File, { rejectWithValue }) => {
    try {
      const formData = new FormData();
      formData.append("file", file);
      const response = await fetch("http://localhost:8080/api/index/law/save", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) return rejectWithValue("Failed to submit law");
      return await response.json();
    } catch (error) {
      return rejectWithValue("Failed to submit law");
    }
  }
);

export const indexLaw = createAsyncThunk<string, Contract>(
  "contarct/indexLaw",
  async (contract: Contract, { rejectWithValue }) => {
    try {
      const response = await fetch("http://localhost:8080/api/index/law", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(contract),
      });

      if (!response.ok) return rejectWithValue("Failed to submit law");
      return await response.json();
    } catch (error) {
      return rejectWithValue("Failed to submit law");
    }
  }
);

export const basicSearch = createAsyncThunk<SearchResults, string>(
  "contract/basicSearch",
  async (query: string, { rejectWithValue }) => {
    try {
      const response = await fetch(`http://localhost:8080/api/search/simple`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ keywords: query.split(' ') }),
      });
      const result = await response.json();
      if (!response.ok) return rejectWithValue("Failed to search");
      return result;
    } catch (error) {
      return rejectWithValue("Failed to search");
    }
  }
);

export const advancedSearch = createAsyncThunk<SearchResults, ParameterObject[]>(
  "contract/advancedSearch",
  async (parameters: ParameterObject[], { rejectWithValue }) => {
    const params = parameters.map((parameter) => {
      return {
        field: parameter.field,
        value: parameter.value,
        operator: parameter.operator,
        phrase: parameter.phrase,
        negation: parameter.equalityOperand === ParameterEqualityOperand.IS_NOT,
      }
    });
    try {
      const response = await fetch(`http://localhost:8080/api/search/advanced`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ parameters: params }),
      });
      const result = await response.json();
      if (!response.ok) return rejectWithValue("Failed to search");
      return result;
    } catch (error) {
      return rejectWithValue("Failed to search");
    }
  }
);