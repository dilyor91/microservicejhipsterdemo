import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AddmissionRulesDetailComponent } from './addmission-rules-detail.component';

describe('AddmissionRules Management Detail Component', () => {
  let comp: AddmissionRulesDetailComponent;
  let fixture: ComponentFixture<AddmissionRulesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddmissionRulesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ addmissionRules: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AddmissionRulesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddmissionRulesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load addmissionRules on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.addmissionRules).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
