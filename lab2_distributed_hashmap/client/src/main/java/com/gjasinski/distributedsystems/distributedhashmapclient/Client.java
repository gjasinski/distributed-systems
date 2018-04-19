package com.gjasinski.distributedsystems.distributedhashmapclient;

		                      def p_elif_block(p):
		                      """elif_block : ELSE IF '(' expression ')' instruction_block
		                      | ELSE IF '(' expression ')' instruction_block elif_block"""
		                      if len(p) > 7:
		                      p[0] = classes.ElIfBlock(p[4], p[6], p[7])
		                      else:
		                      p[0] = classes.ElIfBlock(p[4], p[6], None)


		                      def p_for_instruction(p):
		                      """for_instruction : FOR range instruction_block"""
		                      p[0] = classes.ForInstruction(p[2], p[3])

		                      def p_range(p):
		                      """range : ID '=' expression ':' expression"""
		                      p[0] = classes.Range(p[1], p[3], p[5])
    