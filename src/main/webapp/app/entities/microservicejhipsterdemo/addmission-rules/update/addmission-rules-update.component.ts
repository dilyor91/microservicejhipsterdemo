import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AddmissionRulesFormService, AddmissionRulesFormGroup } from './addmission-rules-form.service';
import { IAddmissionRules } from '../addmission-rules.model';
import { AddmissionRulesService } from '../service/addmission-rules.service';

@Component({
  selector: 'jhi-addmission-rules-update',
  templateUrl: './addmission-rules-update.component.html',
})
export class AddmissionRulesUpdateComponent implements OnInit {
  isSaving = false;
  addmissionRules: IAddmissionRules | null = null;

  editForm: AddmissionRulesFormGroup = this.addmissionRulesFormService.createAddmissionRulesFormGroup();

  constructor(
    protected addmissionRulesService: AddmissionRulesService,
    protected addmissionRulesFormService: AddmissionRulesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addmissionRules }) => {
      this.addmissionRules = addmissionRules;
      if (addmissionRules) {
        this.updateForm(addmissionRules);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const addmissionRules = this.addmissionRulesFormService.getAddmissionRules(this.editForm);
    if (addmissionRules.id !== null) {
      this.subscribeToSaveResponse(this.addmissionRulesService.update(addmissionRules));
    } else {
      this.subscribeToSaveResponse(this.addmissionRulesService.create(addmissionRules));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddmissionRules>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(addmissionRules: IAddmissionRules): void {
    this.addmissionRules = addmissionRules;
    this.addmissionRulesFormService.resetForm(this.editForm, addmissionRules);
  }
}
