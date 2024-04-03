import { configureStore } from "@reduxjs/toolkit";
import contractReducer from "./slices/contract-slice";

export const store = configureStore({
  reducer: {
    contractSlice: contractReducer,
  },
  middleware: getDefaultMiddleware =>
  getDefaultMiddleware({
    serializableCheck: false,
  })
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
