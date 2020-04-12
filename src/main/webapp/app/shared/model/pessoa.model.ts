import { Moment } from 'moment';
import { ITelefone } from 'app/shared/model/telefone.model';
import { TipoPessoa } from 'app/shared/model/enumerations/tipo-pessoa.model';

export interface IPessoa {
  id?: number;
  nome?: string;
  documento?: string;
  dataNascimento?: Moment;
  nomeMae?: string;
  nomePai?: string;
  dataCadastro?: Moment;
  loginOperador?: string;
  tipo?: TipoPessoa;
  telefones?: ITelefone[];
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public nome?: string,
    public documento?: string,
    public dataNascimento?: Moment,
    public nomeMae?: string,
    public nomePai?: string,
    public dataCadastro?: Moment,
    public loginOperador?: string,
    public tipo?: TipoPessoa,
    public telefones?: ITelefone[]
  ) {}
}
