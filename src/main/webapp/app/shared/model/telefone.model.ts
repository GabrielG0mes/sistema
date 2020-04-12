import { Moment } from 'moment';
import { TipoTelefone } from 'app/shared/model/enumerations/tipo-telefone.model';

export interface ITelefone {
  id?: number;
  dDD?: string;
  numero?: string;
  tipo?: TipoTelefone;
  dataCadastro?: Moment;
  loginOperador?: string;
  pessoaId?: number;
}

export class Telefone implements ITelefone {
  constructor(
    public id?: number,
    public dDD?: string,
    public numero?: string,
    public tipo?: TipoTelefone,
    public dataCadastro?: Moment,
    public loginOperador?: string,
    public pessoaId?: number
  ) {}
}
