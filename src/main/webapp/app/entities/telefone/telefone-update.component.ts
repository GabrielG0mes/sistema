import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITelefone, Telefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'jhi-telefone-update',
  templateUrl: './telefone-update.component.html'
})
export class TelefoneUpdateComponent implements OnInit {
  isSaving = false;
  pessoas: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    dDD: [],
    numero: [],
    tipo: [],
    dataCadastro: [],
    loginOperador: [],
    pessoaId: []
  });

  constructor(
    protected telefoneService: TelefoneService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ telefone }) => {
      if (!telefone.id) {
        const today = moment().startOf('day');
        telefone.dataCadastro = today;
      }

      this.updateForm(telefone);

      this.pessoaService.query().subscribe((res: HttpResponse<IPessoa[]>) => (this.pessoas = res.body || []));
    });
  }

  updateForm(telefone: ITelefone): void {
    this.editForm.patchValue({
      id: telefone.id,
      dDD: telefone.dDD,
      numero: telefone.numero,
      tipo: telefone.tipo,
      dataCadastro: telefone.dataCadastro ? telefone.dataCadastro.format(DATE_TIME_FORMAT) : null,
      loginOperador: telefone.loginOperador,
      pessoaId: telefone.pessoaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const telefone = this.createFromForm();
    if (telefone.id !== undefined) {
      this.subscribeToSaveResponse(this.telefoneService.update(telefone));
    } else {
      this.subscribeToSaveResponse(this.telefoneService.create(telefone));
    }
  }

  private createFromForm(): ITelefone {
    return {
      ...new Telefone(),
      id: this.editForm.get(['id'])!.value,
      dDD: this.editForm.get(['dDD'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      dataCadastro: this.editForm.get(['dataCadastro'])!.value
        ? moment(this.editForm.get(['dataCadastro'])!.value, DATE_TIME_FORMAT)
        : undefined,
      loginOperador: this.editForm.get(['loginOperador'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITelefone>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPessoa): any {
    return item.id;
  }
}
