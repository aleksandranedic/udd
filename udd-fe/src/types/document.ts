export interface Contract {
    id?: string;
    governmentName?: string;
    governmentAddress?: string;
    governmentLevel?: string;
    governmentEmail?: string;
    governmentPhone?: string;
    content?: string;
    agencyAddress?: string;
    agencyEmail?: string;
    agencyPhone?: string;
    contractTitle?: string;
    clientSignatoryName?: string;
    clientSignatorySurname?: string;
    agencySignatoryName?: string;
    agencySignatorySurname?: string;
    lawContent?: string;
    highlights?: Record<keyof Contract, string>;
    serverFilename?: string;
}

export const ContractName = Object.freeze({
    governmentName: "Government name",
    governmentAddress: "Government address",
    governmentLevel: "Government level",
    governmentEmail: "Government email",
    governmentPhone: "Government phone",
    content: "Contract content",
    agencyAddress: "Agency address",
    agencyEmail: "Agency email",
    agencyPhone: "Agency phone",
    contractTitle: "Contract title",
    clientSignatoryName: "Client name",
    clientSignatorySurname: "Client surname",
    agencySignatoryName: "Agency name",
    agencySignatorySurname: "Agency surname",
    lawContent: "Law content",
});

export enum DocumentType {
    Contract,
    Law,
}

export interface SearchResults {
    content: Contract[];
    total: number;
    page: number;
    size: number;
}