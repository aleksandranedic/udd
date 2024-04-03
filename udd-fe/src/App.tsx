import React, { useState } from 'react';
import { Sidebar } from './Sidebar';
import { Page } from './types/pages';
import { MainView } from './MainView';
import { Provider } from 'react-redux';
import { store } from './store/store';

function App() {
  const [chosenPage, setChosenPage] = useState(Page.UPLOAD_CONTRACT);
  return (
    <Provider store={store}>
    <div className="flex h-full">
      <Sidebar setChosenPage={setChosenPage}/>
      <MainView chosenPage={chosenPage}/>
    </div>
    </Provider>
  );
}

export default App;
