import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AddmissionRulesFormService } from './addmission-rules-form.service';
import { AddmissionRulesService } from '../service/addmission-rules.service';
import { IAddmissionRules } from '../addmission-rules.model';

import { AddmissionRulesUpdateComponent } from './addmission-rules-update.component';

describe('AddmissionRules Management Update Component', () => {
  let comp: AddmissionRulesUpdateComponent;
  let fixture: ComponentFixture<AddmissionRulesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addmissionRulesFormService: AddmissionRulesFormService;
  let addmissionRulesService: AddmissionRulesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AddmissionRulesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AddmissionRulesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddmissionRulesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addmissionRulesFormService = TestBed.inject(AddmissionRulesFormService);
    addmissionRulesService = TestBed.inject(AddmissionRulesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const addmissionRules: IAddmissionRules = { id: 456 };

      activatedRoute.data = of({ addmissionRules });
      comp.ngOnInit();

      expect(comp.addmissionRules).toEqual(addmissionRules);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddmissionRules>>();
      const addmissionRules = { id: 123 };
      jest.spyOn(addmissionRulesFormService, 'getAddmissionRules').mockReturnValue(addmissionRules);
      jest.spyOn(addmissionRulesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addmissionRules });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addmissionRules }));
      saveSubject.complete();

      // THEN
      expect(addmissionRulesFormService.getAddmissionRules).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addmissionRulesService.update).toHaveBeenCalledWith(expect.objectContaining(addmissionRules));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddmissionRules>>();
      const addmissionRules = { id: 123 };
      jest.spyOn(addmissionRulesFormService, 'getAddmissionRules').mockReturnValue({ id: null });
      jest.spyOn(addmissionRulesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addmissionRules: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addmissionRules }));
      saveSubject.complete();

      // THEN
      expect(addmissionRulesFormService.getAddmissionRules).toHaveBeenCalled();
      expect(addmissionRulesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddmissionRules>>();
      const addmissionRules = { id: 123 };
      jest.spyOn(addmissionRulesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addmissionRules });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addmissionRulesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
