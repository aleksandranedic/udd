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
});

export interface Law {
    id?: string;
    content: string;
}