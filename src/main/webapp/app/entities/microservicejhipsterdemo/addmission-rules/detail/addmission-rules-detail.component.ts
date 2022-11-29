import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddmissionRules } from '../addmission-rules.model';

@Component({
  selector: 'jhi-addmission-rules-detail',
  templateUrl: './addmission-rules-detail.component.html',
})
export class AddmissionRulesDetailComponent implements OnInit {
  addmissionRules: IAddmissionRules | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addmissionRules }) => {
      this.addmissionRules = addmissionRules;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
