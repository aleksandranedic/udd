import { AdvancedSearch } from "./pages/AdvancedSearch";
import { BasicSearch } from "./pages/BasicSearch";
import { Staticstics } from "./pages/Statistics";
import { Page } from "./types/pages";

interface IProps {
    chosenPage: Page;
}

export const MainView: React.FunctionComponent<IProps> = ({ chosenPage }) => {
    return (
        <div className="w-[85%] bg-darker-white shadow-[0_18px_18px_-5px_rgba(0,0,0,0.3)] p-10 ps-20">
            <h2 className="text-5xl pb-4">{chosenPage}</h2>
            <div className="pt-12 h-[95%]">
                {chosenPage === Page.SEARCH && <BasicSearch />}
                {chosenPage === Page.ADVANCE && <AdvancedSearch />}
                {chosenPage === Page.STATISTICS && <Staticstics />}
            </div>
        </div>
    )
};