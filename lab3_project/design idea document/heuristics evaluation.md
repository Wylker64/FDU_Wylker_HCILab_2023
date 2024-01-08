# 启发式评估 Heuristics evaluation

20307130010 郭欣宸

-----


## 1. 尼尔森十大可用性原则(Nielsen's 10 usability heuristics)

1. **系统状态可见性 Visibility of system status**
系统应始终向用户传达适当的信息，让用户了解当前系统状况。

2. **一致性与标准 Consistency and standards**
系统应保持一致性，避免用户需要猜测相同的操作在不同情境下的含义。

3. **用户控制与自由 User control and freedom**
用户应能自由地进入和退出系统的不同部分，特别是在错误操作后能轻松撤销和重做。

4. **错误预防 Error prevention**
设计应减少错误发生的机会，并给予有效的错误信息以防止错误发展。

5. **识别而非回忆 Recognition rather than recall**
系统应减轻用户的记忆负担，使元素、选项和操作可见或容易检索。

6. **灵活与效率的使用 Flexibility and efficiency of use**
系统应为不同水平的用户提供不同的使用方式，以提高效率。

7. **美观与简洁 Aesthetic and minimalist design**
界面应避免不必要的信息，每个额外的信息单元都可能分散用户的注意力。

8. **帮助用户识别、诊断和恢复错误 Help users recognize, diagnose, and recover from errors**
错误信息应简明、具体、正面，指导用户解决问题

9. **帮助和文档 Help and documentation**
即使系统易于使用，有时也需要提供帮助和文档，这些文档应易于搜索和理解，专注于用户的任务。

10. **系统与现实世界之间的匹配 Match between system and the real world**
系统应使用用户熟悉的语言和符号，遵循真实世界的习惯和逻辑。


## 2. 日常软件的启发式评估

根据尼尔森十大可用性原则，对淘宝移动端APP进行启发式评估。

* **系统状态可见性 Visibility of system status**
淘宝在加载或处理数据时通常会有加载指示，让用户知道系统正在工作。例如，在没有网络连接时，会有提示用户没有网络连接的提示。

* **一致性与标准 Consistency and standards**
淘宝在不同页面的操作方式基本一致，例如在商品详情页，用户可以通过点击右上角的按钮分享商品，而在订单详情页，用户也可以通过点击右上角的按钮分享订单。

* **用户控制与自由 User control and freedom**
用户可以轻松地添加商品到购物车、移除商品，以及在浏览过程中自由地前进和后退。

* **错误预防 Error prevention**
在提交订单或进行支付之前，淘宝会提供明确的确认步骤，减少用户操作错误。例如下单后，商家会自动发送确认地址与订单信息的消息，让用户反复确认订单是否有误以及是否需要更改或取消。

* **识别而非回忆 Recognition rather than recall**
淘宝通过提供清晰的图标和标签，帮助用户识别功能，而不是依赖于记忆。

* **灵活与效率的使用 Flexibility and efficiency of use**
***[severity 3-major usability problem]*** 
购物车界面中，对于已被添加到购物车很长一段时间以至已经失效的商品，淘宝没有提供一键删除的功能，需要用户手动检查购物车所有物品并进行删除。这在用户购物车接近上限时尤其明显。

* **美观与简洁 Aesthetic and minimalist design**
***[severity 1-cosmetic problem]*** 
如下图所示，淘宝首页有过多频道占据了大半个屏幕，而这些都与用户的购物行为无关，用户需要向下滑动才能浏览与自己购物相关的内容。
<img src=1107c3c08e77832f8ced916011a4fbb.png height="400">

* **帮助用户识别、诊断和恢复错误 Help users recognize, diagnose, and recover from errors**
淘宝会在订单提交失败时提供明确的错误信息，淘宝会告知用户订单提交失败的原因，以及如何解决。

* **帮助和文档 Help and documentation**
淘宝在用户使用过程中会提供帮助，其常用提供帮助的方式是通过客服。用户在遇到问题时可以通过点击“我的淘宝”界面右上角的客服按钮，与客服人员进行沟通。

* **系统与现实世界之间的匹配 Match between system and the real world**
淘宝的界面主要由商品构成，用户通过点击商品的方式来进行商品详情的查看与购买，这与用户在现实世界中购买商品的方式相匹配。