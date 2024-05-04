// counterSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Contract } from "../../types/document";
import { advancedSearch, basicSearch, indexContract, indexLaw, submitContract, submitLaw } from "../actions/contract-actions";

interface ContractState {
  contract: Contract | null;
  loading: boolean;
  searchResults: Contract[] | null;
  total: number;
  page: number;
  size: number;
}

const initialState: ContractState = {
  contract: null,
  loading: false,
  searchResults: null,
  total: 0,
  page: 0,
  size: 0,
};

export const contractSlice = createSlice({
  name: "contract",
  initialState,
  reducers: {
    setContract: (state, action: PayloadAction<Partial<Contract>>) => {
      state = { ...state, contract: state.contract !== null ? {...state.contract,...action.payload }: {...action.payload}};
      return state;
    },
    resetContract: (state) => {
      state = { ...state, contract: null };
      return state;
    },
    resetSearchResults: (state) => {
      state = { ...state, searchResults: null, page: 0, size: 0, total: 0};
      return state;
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
        return state;
      }
    );
    builder.addCase(submitContract.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
    builder.addCase(indexContract.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(indexContract.fulfilled, (state: ContractState, action) => {
        alert(action.payload);
        return state;
      }
    );
    builder.addCase(indexContract.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });

    builder.addCase(submitLaw.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(
      submitLaw.fulfilled,
      (state: ContractState, action) => {
        const newContract = action.payload as Contract;
        state = { ...state, contract: newContract, loading: false };
        return state;
      }
    );
    builder.addCase(submitLaw.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
    builder.addCase(indexLaw.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(indexLaw.fulfilled, (state: ContractState, action) => {
        alert(action.payload);
        return state;
      }
    );
    builder.addCase(indexLaw.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
    builder.addCase(basicSearch.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(basicSearch.fulfilled, (state: ContractState, action) => {
        state.searchResults = action.payload.content;
        state.page = action.payload.page;
        state.size = action.payload.size;
        state.total = action.payload.total;
        return state;
      }
    );
    builder.addCase(basicSearch.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
    builder.addCase(advancedSearch.pending, (state: ContractState) => {
      state = { ...state, loading: true };
      return state;
    });
    builder.addCase(advancedSearch.fulfilled, (state: ContractState, action) => { 
      console.log(action.payload)
      state.searchResults = action.payload.content;
      state.page = action.payload.page;
      state.size = action.payload.size;
      state.total = action.payload.total;
      return state;
      }
    );
    builder.addCase(advancedSearch.rejected, (state: ContractState) => {
      state = {...state, loading: false};
      return state;
    });
  },
});

export const { setContract, resetContract, resetSearchResults } = contractSlice.actions;

export default contractSlice.reducer;
