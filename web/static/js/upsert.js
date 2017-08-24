(function () {
  var eSkills = document.getElementById('skills');
  var eSkillsContainer = document.getElementById('skills-container');
  var skillIds = [];

  function removeSkill(skillId) {
    var eSkill = document.getElementById('skill-' + skillId);
    eSkill.parentNode.removeChild(eSkill);
    skillIds.splice(skillIds.indexOf(skillId), 1);
  }

  function addSkill(_skillValue) {
    var skillValue = _skillValue || eSkills.value;
    // skill value format {id}-{name}
    var skillArray = skillValue.split('-');
    var skill = {
      id: skillArray[0],
      name: skillArray[1]
    };

    if(skillIds.indexOf(skill.id) > -1) {
      alert('Skill already added');
      return;
    } else {
      skillIds.push(skill.id);
    }

    var eSkill = document.createElement('div');
    eSkill.setAttribute('id', 'skill-' + skill.id);
    eSkill.setAttribute('class', 'upsert__skill');
    eSkill.innerHTML =  skill.name + '<span title="Remove Skill" class="upsert__skill-delete" onclick="removeSkill(\'' + skill.id + '\')">X</span>';
    eSkillsContainer.appendChild(eSkill);
  }
  
  function submitForm() {
    var eUpsertForm = document.getElementById("upsert-form");
    var eSkillsIdsInput = document.createElement("input");
    eSkillsIdsInput.setAttribute('name', 'skillsIds');
    eSkillsIdsInput.setAttribute('type', 'hidden');
    eSkillsIdsInput.setAttribute('value', skillIds.toString());
    eUpsertForm.appendChild(eSkillsIdsInput);
    eUpsertForm.submit();
  }

  initialSkills.forEach(function (skillId) {
    addSkill(skillId);
  });

  // export function
  window.addSkill = addSkill;
  window.removeSkill = removeSkill;
  window.submitForm = submitForm;

})();