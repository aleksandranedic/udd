import { resetContract, resetSearchResults } from "./store/slices/contract-slice";
import { useAppDispatch } from "./store/types";
import { Page } from "./types/pages";
import { SidebarItem, sidebarMenu } from "./types/sidebar-items";

interface IProps {
    setChosenPage: React.Dispatch<React.SetStateAction<Page>>
}

export const Sidebar: React.FunctionComponent<IProps> = ({setChosenPage}) => {
    const dispatch = useAppDispatch();
    const setPage = (subItem: SidebarItem) => {
        setChosenPage(subItem.page);
        dispatch(resetContract())
        dispatch(resetSearchResults())
    }
    return (
        <div className="w-[15%] flex flex-col p-6 gap-12 bg-off-white h-full shadow-[-10_8px_8px_0px_rgba(0,0,0,0.3)]">
            <h2 className="text-3xl font-normal tracking-wider pb-7 border-b border-gray-light ps-6">LawRepo</h2>
            <div className="flex flex-col items-start gap-3">
                {sidebarMenu.map((item, ind) => (
                    <div className={`${ind !== 0 ? 'border-t pt-5' : ''} border-light-gray w-full pb-5`}>
                        <h3 className="text-xl tracking-wider pt-4 pb-2 ps-6">{item.name}</h3>
                        {item.items.map((subItem) => (
                            <button key={subItem.page} className="flex items-center w-full rounded-xl hover:bg-secondary hover:shadow-2xl" onClick={() => setPage(subItem)}>
                            <span className=" flex w-full p-3 gap-7 ps-5 font-light hover:text-off-white">
                                {subItem.icon({size: 20})}
                                {subItem.name}
                                </span>
                            </button>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    )
};