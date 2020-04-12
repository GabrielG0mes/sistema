export interface IUser {
  id?: any;
  login?: string;
  nome?: string;
  perfis?: string[];
  dataCadastro?: Date;
  senha?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public nome?: string,
    public perfis?: string[],
    public dataCadastro?: Date,
    public senha?: string
  ) {}
}
