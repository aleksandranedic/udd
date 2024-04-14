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

export interface Law {
    id?: string;
    content: string;
}