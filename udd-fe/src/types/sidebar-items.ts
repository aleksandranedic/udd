import { IconType } from "react-icons";
import { Page } from "./pages";
import * as TbIcons from "react-icons/tb";
import { VscGraph } from "react-icons/vsc";

export interface SidebarMenu {
    name: string;
    items: SidebarItem[];
}

export interface SidebarItem {
    icon: IconType;
    name: string;
    page: Page;
}

export const sidebarMenu: SidebarMenu[] =  [
    {
        name: "Search",
        items: [{
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
        }]
    },
    {
        name: "Files",
        items: [{
            icon: TbIcons.TbFileCv,
            name: "Upload contract",
            page: Page.UPLOAD_CONTRACT
        },
        {
            icon: TbIcons.TbFile,
            name: "Upload law",
            page: Page.UPLOAD_LAW
        }]
    }
];