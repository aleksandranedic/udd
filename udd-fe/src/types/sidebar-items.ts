import { IconType } from "react-icons";
import { Page } from "./pages";
import * as TbIcons from "react-icons/tb";
import { VscGraph } from "react-icons/vsc";

export interface SidebarItem {
    icon: IconType;
    name: string;
    page: Page;
}

export const sidebarItems: SidebarItem[] = [
    {
        icon: TbIcons.TbSearch,
        name: "Basic search",
        page: Page.SEARCH,
    },
    {
        icon: TbIcons.TbListSearch,
        name: "Advanced search",
        page: Page.ADVANCE
    },
    {
        icon: VscGraph,
        name: "Statistics",
        page: Page.STATISTICS
    }
];