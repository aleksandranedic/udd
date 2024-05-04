export enum Page {
    SEARCH = "Basic search",
    ADVANCE = "Advanced search",
    STATISTICS = "Statistics",
    UPLOAD_CONTRACT = "Upload contract",
    UPLOAD_LAW = "Upload law",
}

export enum ParameterField {
    EMPLOYEE_NAME = "Employee name",
    EMPLOYEE_SURNAME = "Employee surname",
    GOVERMENT = "Government name",
    ADMINISTRATIVE_LEVEL = "Administrative level",
    CONTRACT = "Contract content",
    LAW = "Law content",
}

export enum ParameterOperand {
    AND = "AND",
    OR = "OR",
}

export enum ParameterEqualityOperand {
    IS = "is",
    IS_NOT = "is not",
}

export class ParameterObject {
    constructor(public field: ParameterField, public equalityOperand: ParameterEqualityOperand, public value: string, public phrase: boolean, public operator?: ParameterOperand) {
        this.field = field;
        this.equalityOperand = equalityOperand;
        this.value = value;
        this.operator = operator;
        this.phrase = phrase;
    }
}