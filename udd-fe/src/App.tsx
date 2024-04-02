import React, { useState } from 'react';
import { Sidebar } from './Sidebar';
import { Page } from './types/pages';
import { MainView } from './MainView';

function App() {
  const [chosenPage, setChosenPage] = useState(Page.UPLOAD_CONTRACT);
  return (
    <div className="flex h-full">
      <Sidebar setChosenPage={setChosenPage}/>
      <MainView chosenPage={chosenPage}/>
    </div>
  );
}

export default App;
