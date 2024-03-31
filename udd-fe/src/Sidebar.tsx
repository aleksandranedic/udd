import { Page } from "./types/pages";
import { sidebarItems } from "./types/sidebar-items";

interface IProps {
    setChosenPage: React.Dispatch<React.SetStateAction<Page>>
}

export const Sidebar: React.FunctionComponent<IProps> = ({setChosenPage}) => {
    return (
        <div className="w-[15%] flex flex-col p-6 gap-12 bg-off-white h-full shadow-[-10_8px_8px_0px_rgba(0,0,0,0.3)]">
            <h2 className="text-3xl font-normal tracking-wider pb-4 border-b border-gray-light ps-6">LawRepo</h2>
            <div className="flex flex-col items-start gap-3">
                {sidebarItems.map((item) => (
                    <button key={item.page} className="flex items-center w-full rounded-xl hover:bg-secondary hover:shadow-2xl" onClick={() => setChosenPage(item.page)}>
                    <span className="text-xl flex w-full p-3 gap-7 ps-5 font-light hover:text-off-white">
                        {item.icon({size: 27})}
                        {item.name}
                        </span>
                    </button>
                ))}
</div>
        </div>
    )
};