export interface Contract {
    id?: string;
    governmentName?: string;
    governmentAddress?: string;
    governmentAdministrativeLevel?: string;
    governmentEmail?: string;
    governmentPhone?: string;
    content?: string;
    agencyEmployeeName?: string;
    agencyAddress?: string;
    agencyEmail?: string;
    agencyPhone?: string;
    title?: string;
    governmentEmployeeName?: string;
}

export interface Law {
    id?: string;
    content: string;
}