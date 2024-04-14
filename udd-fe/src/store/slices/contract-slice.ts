// counterSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Contract } from "../../types/document";
import { submitContract } from "../actions/contract-actions";

interface ContractState {
  contract: Contract | null;
  loading: boolean;
}

const initialState: ContractState = {
  contract: null,
  loading: false,
};

export const contractSlice = createSlice({
  name: "contract",
  initialState,
  reducers: {
    setContract: (state, action: PayloadAction<Partial<Contract>>) => {
      state.contract = state.contract !== null ? {...state.contract,...action.payload }: {...action.payload};
      state.loading = false;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(submitContract.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(
      submitContract.fulfilled,
      (state: ContractState, action) => {
        const newContract = action.payload as Contract;
        state = { ...state, contract: newContract, loading: false };
        console.log(state);
        return state;
      }
    );
    builder.addCase(submitContract.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
  },
});

export const { setContract } = contractSlice.actions;

export default contractSlice.reducer;
