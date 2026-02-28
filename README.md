# TinyLobby

A lightweight lobby plugin for Minecraft servers running PaperMC. This plugin displays a persistent, user-friendly GUI,
allowing players to select and join servers from a list.

## Prerequisites

|             | Version |
|-------------|:-------:|
| Java        |   21    |
| Minecraft   | 1.21.3  |
| Server Type | PaperMC |

## Suggested world setup

Set the following properties in `server.properties`:

| Property              | Value                                                                                |
|-----------------------|--------------------------------------------------------------------------------------|
| `generate-structures` | `false`                                                                              |
| `generator-settings`  | `{"biome"\:"minecraft\:plains","layers"\:[{"block"\:"minecraft\:air","height"\:1}]}` |
| `level-type`          | `minecraft\:flat`                                                                    |
