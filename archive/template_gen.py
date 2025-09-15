#!/usr/bin/env python3
"""
Script to generate modpack introduction templates with different modpack names.
"""

import json
import os

def generate_modpack_templates():
  # Template structure
  template = {
    "identifier": "modpack_introduction",
    "schedule": "on_first_join",
    "type": "chat",
    "repeats": False,
    "pack_intro": True,
    "link": "https://bisecthosting.com/Lupin",
    "text": "§2Thank you very much for downloading $MODPACK_NAME$§f \n §aDid you know that this modpack can be played in multiplayer? Click here to purchase a server and play with your friends.§f \n§cDon't worry, this message will not appear again.§f"
  }

  # List of modpack names - modify this list with your actual modpack names
  modpack_names = [
    "New Game",
    "Prehistoric World",
    "Essentials Mods",
    "More Animals",
    "New Simple Mods",
    "New Game 2",
    "More FPS",
    "More Decorations",
    "New Revolution",
    "More Realistic",
    "New Age",
    "More Dungeons",
    "The Zombie Conquest",
    "The Last War",
    "Mastering Magic",
    "Prehistoric Dawn",
    "More Biomes",
    "Conquest of Dragons",
    "New Simple Mods",
    "More Decorations 2",
    "The Zombie Quest - Easy Mode",
    "More Animals 2",
    "Immersive Pixelmon",
    "Whispers in the Void",
    "Create Evolution",
    "Biohazard: Project Genesis"
  ]

  # Generate templates for each modpack
  generated_templates = []

  for modpack_name in modpack_names:
    # Create a copy of the template
    current_template = template.copy()

    # Replace the placeholder with the actual modpack name
    current_template["text"] = current_template["text"].replace("$MODPACK_NAME$", modpack_name)

    # Add to the list
    generated_templates.append(current_template)

  return generated_templates

def write_templates_to_file(templates, filename="modpack_templates.json"):
  """
  Write the generated templates to a file.
  You can choose between different output formats.
  """

  # Option 1: Write as a JSON array (recommended)
  with open(filename, 'w', encoding='utf-8') as f:
    json.dump(templates, f, indent=2, ensure_ascii=False)

  print(f"Generated {len(templates)} templates and saved to '{filename}'")

  # Option 2: Write as individual JSON objects (uncomment if preferred)
  # individual_filename = "modpack_templates_individual.txt"
  # with open(individual_filename, 'w', encoding='utf-8') as f:
  #     for i, template in enumerate(templates):
  #         if i > 0:
  #             f.write('\n\n')  # Add spacing between templates
  #         f.write(json.dumps(template, indent=2, ensure_ascii=False))
  #
  # print(f"Also saved individual templates to '{individual_filename}'")

def main():
  print("Generating modpack templates...")

  # Generate the templates
  templates = generate_modpack_templates()

  # Write to file
  write_templates_to_file(templates)

  # Display summary
  print(f"\nGenerated templates for the following modpacks:")
  for i, template in enumerate(templates, 1):
    # Extract modpack name from the text field
    text = template["text"]
    start = text.find("downloading ") + len("downloading ")
    end = text.find("§f", start)
    modpack_name = text[start:end]
    print(f"{i:2d}. {modpack_name}")

if __name__ == "__main__":
  main()